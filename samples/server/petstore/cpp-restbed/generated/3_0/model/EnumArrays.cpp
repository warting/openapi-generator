/**
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI-Generator 7.15.0-SNAPSHOT.
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */



#include "EnumArrays.h"

#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <stdexcept>
#include <regex>
#include <algorithm>
#include <boost/lexical_cast.hpp>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/json_parser.hpp>
#include "helpers.h"

using boost::property_tree::ptree;
using boost::property_tree::read_json;
using boost::property_tree::write_json;

namespace org {
namespace openapitools {
namespace server {
namespace model {

EnumArrays::EnumArrays(boost::property_tree::ptree const& pt)
{
        fromPropertyTree(pt);
}


std::string EnumArrays::toJsonString(bool prettyJson /* = false */) const
{
	std::stringstream ss;
	write_json(ss, this->toPropertyTree(), prettyJson);
    // workaround inspired by: https://stackoverflow.com/a/56395440
    std::regex reg("\\\"([0-9]+\\.{0,1}[0-9]*)\\\"");
    std::string result = std::regex_replace(ss.str(), reg, "$1");
    return result;
}

void EnumArrays::fromJsonString(std::string const& jsonString)
{
	std::stringstream ss(jsonString);
	ptree pt;
	read_json(ss,pt);
	this->fromPropertyTree(pt);
}

ptree EnumArrays::toPropertyTree() const
{
	ptree pt;
	ptree tmp_node;
	pt.put("just_symbol", m_Just_symbol);
	// generate tree for Array_enum
    tmp_node.clear();
	if (!m_Array_enum.empty()) {
        tmp_node = toPt(m_Array_enum);
		pt.add_child("array_enum", tmp_node);
		tmp_node.clear();
	}
	return pt;
}

void EnumArrays::fromPropertyTree(ptree const &pt)
{
	ptree tmp_node;
	setJustSymbol(pt.get("just_symbol", ""));
	// push all items of Array_enum into member
	if (pt.get_child_optional("array_enum")) {
        m_Array_enum = fromPt<std::vector<std::string>>(pt.get_child("array_enum"));
	}
}

std::string EnumArrays::getJustSymbol() const
{
    return m_Just_symbol;
}

void EnumArrays::setJustSymbol(std::string value)
{
    static const std::array<std::string, 2> allowedValues = {
        ">=", "$"
    };

    if (std::find(allowedValues.begin(), allowedValues.end(), value) != allowedValues.end()) {
		m_Just_symbol = value;
	} else {
		throw std::runtime_error("Value " + boost::lexical_cast<std::string>(value) + " not allowed");
	}
}


std::vector<std::string> EnumArrays::getArrayEnum() const
{
    return m_Array_enum;
}

void EnumArrays::setArrayEnum(std::vector<std::string> value)
{
    static const std::array<std::string, 2> allowedValues = {
        "fish", "crab"
    };

    for (const auto &v: value) {
        if (std::find(allowedValues.begin(), allowedValues.end(), v) == allowedValues.end()) {
            throw std::runtime_error("Value " + boost::lexical_cast<std::string>(v) + " not allowed");
        }
    }
}



std::vector<EnumArrays> createEnumArraysVectorFromJsonString(const std::string& json)
{
    std::stringstream sstream(json);
    boost::property_tree::ptree pt;
    boost::property_tree::json_parser::read_json(sstream,pt);

    auto vec = std::vector<EnumArrays>();
    for (const auto& child: pt) {
        vec.emplace_back(EnumArrays(child.second));
    }

    return vec;
}

}
}
}
}

